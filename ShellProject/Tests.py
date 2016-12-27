import unittest
from ShellProject.Tokenizer import *
from ShellProject.CommandSeparator import *
from ShellProject.Commands import *
from ShellProject.CommandExecutor import *


class TokenizerTests(unittest.TestCase):
    def test_substitution(self):
        self.environment = {'FILE': 'example.txt', 'name': 'Peter', 'FILE2': 'input.in'}

        self.input_stream1 = "cat $FILE | wc"
        self.input_stream2 = "echo \"Hello, $name\""
        self.input_stream3 = "cat '$FILE2' | wc"

        self.assertEqual(Tokenizer.substitute_variables(self.input_stream1, self.environment), "cat example.txt | wc")
        self.assertEqual(Tokenizer.substitute_variables(self.input_stream2, self.environment), "echo \"Hello, Peter\"")
        self.assertEqual(Tokenizer.substitute_variables(self.input_stream3, self.environment), "cat '$FILE2' | wc")

    def test_tokenizing(self):
        self.environment = {'FILE': 'example.txt', 'name': 'Peter', 'FILE2': 'input.in'}

        self.input_stream1 = "echo \"Hello, $name\""
        self.tokens1 = ['echo', 'Hello, Peter']
        self.input_stream2 = "cat '$FILE2'|wc"
        self.tokens2 = ['cat', '$FILE2', '|', 'wc']
        self.input_stream3 = "echo \"Hello, $name\"|date=1703|wc"
        self.tokens3 = ['echo', 'Hello, Peter', '|', 'date', '=', '1703', '|', 'wc']

        self.input_stream4 = "echo 1"
        self.tokens4 = ['echo', '1']

        self.assertEqual(Tokenizer.tokenize_command_string(self.input_stream1, self.environment), self.tokens1)
        self.assertEqual(Tokenizer.tokenize_command_string(self.input_stream2, self.environment), self.tokens2)
        self.assertEqual(Tokenizer.tokenize_command_string(self.input_stream3, self.environment), self.tokens3)
        self.assertEqual(Tokenizer.tokenize_command_string(self.input_stream4, self.environment), self.tokens4)


class SeparatorTests(unittest.TestCase):
    def test_separation_by_pipe(self):
        self.environment = {'FILE': 'example.txt', 'name': 'Peter', 'FILE2': 'input.in'}
        self.input_stream = "FILE='resources/example.txt' | cat $FILE"

        self.tokens = Tokenizer.tokenize_command_string(self.input_stream, self.environment)
        self.commands = [['assign', 'FILE', 'resources/example.txt'], ['cat', 'example.txt']]

        self.assertEqual(CommandSeparator.separate_by_pipe(self.tokens), self.commands)

class CommandsTests(unittest.TestCase):
    def test_grep_ignore_case(self):
        self.environment = {}
        self.input_stream = ""
        self.output_stream = ""

        command_string = "grep -i hello resources/example.txt"
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        Tokenizer.update_environment(tokens, self.environment)
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        commands = CommandSeparator.separate_by_pipe(tokens)
        CommandExecutor.execute(self, commands)

        self.assertEqual(self.input_stream, "Hello, world! This is test file for cat_command.\n")

    def test_grep_whole_word(self):
        self.environment = {}
        self.input_stream = ""
        self.output_stream = ""

        command_string = "grep -w run resources/example.txt"
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        Tokenizer.update_environment(tokens, self.environment)
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        commands = CommandSeparator.separate_by_pipe(tokens)
        CommandExecutor.execute(self, commands)

        self.assertEqual(self.input_stream, "run, Peter, run!\n")

    def test_grep_whole_word_ignore_case(self):
        self.environment = {}
        self.input_stream = ""
        self.output_stream = ""

        command_string = "grep -i -w run resources/example.txt"
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        Tokenizer.update_environment(tokens, self.environment)
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        commands = CommandSeparator.separate_by_pipe(tokens)
        CommandExecutor.execute(self, commands)

        self.assertEqual(self.input_stream, "run, Peter, run!\nRun\n")

    def test_grep_n_lines_ignore_case(self):
        self.environment = {}
        self.input_stream = ""
        self.output_stream = ""

        command_string = "grep -i -A 3 hello resources/example.txt"
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        Tokenizer.update_environment(tokens, self.environment)
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        commands = CommandSeparator.separate_by_pipe(tokens)
        CommandExecutor.execute(self, commands)

        self.assertEqual(self.input_stream, "\nHello, world! This is test file for cat_command.\n"
                                            "And now it's also test file for grep command\nWe need more lines!\n"
                                            "More!")

    def test_grep_without_flags(self):
        self.environment = {}
        self.input_stream = ""
        self.output_stream = ""

        command_string = "grep hello resources/example.txt"
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        Tokenizer.update_environment(tokens, self.environment)
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        commands = CommandSeparator.separate_by_pipe(tokens)
        CommandExecutor.execute(self, commands)

        self.assertEqual(self.input_stream, "")

    def test_wrong_grep(self):
        self.environment = {}
        self.input_stream = ""
        self.output_stream = ""

        command_string = "grep"
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        Tokenizer.update_environment(tokens, self.environment)
        tokens = Tokenizer.tokenize_command_string(command_string, self.environment)
        commands = CommandSeparator.separate_by_pipe(tokens)
        CommandExecutor.execute(self, commands)

        self.assertEqual(self.input_stream, "Grep: the following arguments are required: pattern, file(optional).")