from ShellProject.Tokenizer import *
from ShellProject.CommandSeparator import *
from ShellProject.CommandExecutor import *


# This class includes all necessary information about our program's state at the current moment
class ProgramState:
    environment = {}
    input_stream = ""
    output_stream = ""
    is_program_running = True

# It is a main cycle, in which we get command string and then parse commands and execute them
while ProgramState.is_program_running:
    command_string = input(">>> ")
    tokens = Tokenizer.tokenize_command_string(command_string, ProgramState.environment)
    Tokenizer.update_environment(tokens, ProgramState.environment)
    tokens = Tokenizer.tokenize_command_string(command_string, ProgramState.environment)
    commands = CommandSeparator.separate_by_pipe(tokens)
    CommandExecutor.execute(ProgramState, commands)

    if ProgramState.input_stream != '':
        print(ProgramState.input_stream)
