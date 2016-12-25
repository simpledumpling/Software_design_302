from Commands import *

#Create list of arguments for the command given as parameter
def create_args_list(command):
    args = []
    for i in range(1, len(command)):
        args.append(command[i])

    return args

#Execute commands from commands list
def execute(program_state, commands):
    for command in commands:
        args = create_args_list(command)
        if command[0] == "assign":
            assign_command(program_state, args)
        elif command[0] == "cat":
            cat_command(program_state, args)
        elif command[0] == "echo":
            echo_command(program_state, args)
        elif command[0] == "wc":
            wc_command(program_state, args)
        elif command[0] == "pwd":
            pwd_command(program_state, args)
        elif command[0] == "exit":
            exit_command(program_state, args)
        pipe_command(program_state, [])