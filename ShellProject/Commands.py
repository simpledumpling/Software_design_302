from os import getcwd


def assign_command(program_state, args):
    program_state.environment[args[0]] = args[1]


def cat_command(program_state, args):
    program_state.output_stream = ""

    if args:
        for arg in args:
            try:
                file_name = open(arg)
                file_content = file_name.read()
            except FileNotFoundError:
                print("File with this name does not exist")
                break

            program_state.output_stream += file_content
        program_state.input_stream = ""
    else:
        program_state.output_stream = program_state.input_stream
        program_state.input_stream = ""


def echo_command(program_state, args):

    for arg in args:
        program_state.output_stream += str(arg) + " "


def wc_command(program_state, args):
    program_state.output_stream = ""
    if args:
        for arg in args:
            try:
                file_name = open(arg)
            except FileNotFoundError:
                print("File with this name does not exist")
                break
            file_content = file_name.read()
            lines_amount = len(file_content.split('\n'))
            words_amount = len(file_content.split(' '))
            bytes_amount = len(file_content)

            program_state.output_stream = str(lines_amount) + " " + str(words_amount) + " " + str(bytes_amount)
        program_state.input_stream = ""
    else:
        input = program_state.input_stream
        words_amount = 0
        if input != "":
            if input[len(input) - 1] == " ":
                words_amount = -1
        else:
            words_amount = -1
        lines_amount = len(input.split('\n'))
        words_amount += len(input.split(' '))
        bytes_amount = len(input)

        program_state.output_stream = str(lines_amount) + " " + str(words_amount) + " " + str(bytes_amount)
        program_state.input_stream = ""


def pwd_command(program_state, _):
    program_state.input_stream = ""
    program_state.output_stream = getcwd()


def exit_command(program_state, _):
    program_state.input_stream = ""
    program_state.output_stream = ""
    program_state.is_program_running = False


def pipe_command(program_state, _):
    program_state.input_stream = program_state.output_stream
    program_state.output_stream = ""