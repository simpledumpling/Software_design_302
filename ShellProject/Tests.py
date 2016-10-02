import unittest
from Tokenizer import *
from CommandSeparator import *


class TokenizerTests(unittest.TestCase):

    def test_substitution(self):
        self.environment = {'FILE': 'example.txt', 'name': 'Peter', 'FILE2': 'input.in'}

        self.input_stream1 = "cat $FILE | wc"
        self.input_stream2 = "echo \"Hello, $name\""
        self.input_stream3 = "cat '$FILE2' | wc"

        self.assertEqual(substitute_variables(self.input_stream1, self.environment), "cat example.txt | wc")
        self.assertEqual(substitute_variables(self.input_stream2, self.environment), "echo \"Hello, Peter\"")
        self.assertEqual(substitute_variables(self.input_stream3, self.environment), "cat '$FILE2' | wc")

    def test_tokenizing(self):
        self.environment = {'FILE': 'example.txt', 'name': 'Peter', 'FILE2': 'input.in'}

        self.input_stream1 = "echo \"Hello, $name\""
        self.tokens1 = ['echo', 'Hello, Peter']
        self.input_stream2 = "cat '$FILE2'|wc"
        self.tokens2 = ['cat', '$FILE2', '|', 'wc']
        self.input_stream3 = "echo \"Hello, $name\"|date=1703|wc"
        self.tokens3 = ['echo', 'Hello, Peter', '|', 'date', '=', '1703', '|', 'wc']

        self.assertEqual(tokenize_command_string(self.input_stream1, self.environment), self.tokens1)
        self.assertEqual(tokenize_command_string(self.input_stream2, self.environment), self.tokens2)
        self.assertEqual(tokenize_command_string(self.input_stream3, self.environment), self.tokens3)


class SeparatorTests(unittest.TestCase):

    def test_separation_by_pipe(self):
        self.environment = {'FILE': 'example.txt', 'name': 'Peter', 'FILE2': 'input.in'}
        self.input_stream = "echo \"Hello, $name\"|date   =    1703|wc"

        self.tokens = tokenize_command_string(self.input_stream, self.environment)
        self.commands = [['echo', 'Hello, Peter'], ['assign', 'date', '1703'], ['wc']]

        self.assertEqual(separate_by_pipe(self.tokens), self.commands)