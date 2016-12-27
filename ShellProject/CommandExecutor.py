from ShellProject.Commands import *


class CommandExecutor:
    # Create list of arguments for the command given as parameter
    def create_args_list(command):
        args = []
        for i in range(1, len(command)):
            args.append(command[i])

        return args


    # Execute commands from commands list
    def execute(program_state, commands):
        for command in commands:
            args = CommandExecutor.create_args_list(command)
            if command[0] == "assign":
                Commands.assign_command(program_state, args)
            elif command[0] == "cat":
                Commands.cat_command(program_state, args)
            elif command[0] == "echo":
                Commands.echo_command(program_state, args)
            elif command[0] == "wc":
                Commands.wc_command(program_state, args)
            elif command[0] == "pwd":
                Commands.pwd_command(program_state, args)
            elif command[0] == "exit":
                Commands.exit_command(program_state, args)
            elif command[0] == "grep":
                Commands.grep_command(program_state, args)
            Commands.pipe_command(program_state, [])
