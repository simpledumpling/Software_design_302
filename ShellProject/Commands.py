from os import getcwd
import argparse
import re


class Commands:
    # Assign command add variable to the environment
    def assign_command(program_state, args):
        program_state.environment[args[0]] = args[1]

    # Cat command opens files, given as parameters and write content to the output
    def cat_command(program_state, args):
        program_state.output_stream = ""

        # First case - if we have files as parameters
        if args:
            for arg in args:
                # For each file we try to open it and add it's content to the output
                try:
                    file_name = open(arg)
                    file_content = file_name.read()
                except FileNotFoundError:
                    print("Cat: File with this name does not exist.")
                    break

                program_state.output_stream += file_content
            program_state.input_stream = ""
        # Second case - if we haven't got parameters then we write all we have in input stream to the output
        else:
            program_state.output_stream = program_state.input_stream
            program_state.input_stream = ""

    # Echo command write parameter string to the output
    def echo_command(program_state, args):
        for arg in args:
            program_state.output_stream += str(arg) + " "
        program_state.input_stream = ""

    # Wc command write number of words, lines and bytes to the output
    def wc_command(program_state, args):
        program_state.output_stream = ""

        # If we have file(s) name(s) as parameter, we do all calculations for this file(s)
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
        # If we haven't got file(s) name(s), we do all calculations for input
        else:
            input_value = program_state.input_stream
            words_amount = 0
            if input_value != "":
                if input_value[len(input_value) - 1] == " ":
                    words_amount = -1
            else:
                words_amount = -1
            lines_amount = len(input_value.split('\n'))
            words_amount += len(input_value.split(' '))
            bytes_amount = len(input_value)

            program_state.output_stream = str(lines_amount) + " " + str(words_amount) + " " + str(bytes_amount)
            program_state.input_stream = "Hello!"  # Pwd command returns current directory using getcwd command

    def pwd_command(program_state, _):
        program_state.input_stream = ""
        program_state.output_stream = getcwd()

    # Exit command set input and output streams to "" and set is_program_running flag to False
    def exit_command(program_state, _):
        program_state.input_stream = ""
        program_state.output_stream = ""
        program_state.is_program_running = False

    # Pipe command inverse streams - output_stream now is an input_stream and output_stream is empty
    def pipe_command(program_state, _):
        program_state.input_stream = program_state.output_stream
        program_state.output_stream = ""

    # Grep command using argparse
    def grep_command(program_state, args):
        if args:
            # Create parser using argparse
            parser = argparse.ArgumentParser()

            # add arguments for three flags, patterns and files
            parser.add_argument('-i', action='store_true')
            parser.add_argument('-w', action='store_true')
            parser.add_argument('-A', action='store', type=int)
            parser.add_argument('pattern', action='store')
            parser.add_argument('file', action='store', nargs='*')

            args = parser.parse_args(args)
            program_state.output_stream = ""

            # If we haven't files as parameters, we should search in current input_stream
            # If we have some files as parameters, we should search in this files
            # for this we delete all from input_stream and add files' content to the input_stream
            if len(args.file) > 0:
                program_state.input_stream = ""
                for arg in args.file:
                    file_content = open(arg)
                    program_state.input_stream += file_content.read()

            # If we have -w flag, we should search only whole words
            if args.w:
                args.pattern = "\\b{}\\b".format(args.pattern)

            # Create lines array
            lines = program_state.input_stream.split('\n')
            number_of_lines = len(lines)

            # And now we can start searching, using re to check the matches
            for line in range(number_of_lines):
                # Check, if we have -i flag, we should search ignoring case
                if (args.i):
                    search_result = re.search(args.pattern, lines[line], flags=re.IGNORECASE)
                else:
                    search_result = re.search(args.pattern, lines[line])

                # If the result exists, we write current string to the output
                # If we also have -A flag, we should write current string and the next n strings to the output
                if search_result:
                    if args.A is None:
                        program_state.output_stream += lines[line] + "\n"
                    else:
                        for next_line in range(args.A + 1):
                            if line + next_line < number_of_lines:
                                program_state.output_stream += "\n" + lines[line + next_line]

            program_state.input_stream = ""
        else:
            program_state.output_stream = "Grep: the following arguments are required: pattern, file(optional)."
