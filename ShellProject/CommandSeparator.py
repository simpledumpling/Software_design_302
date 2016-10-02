commands_names = [
    'cat',
    'echo',
    'wc',
    'pwd',
    'exit'
]


def separate_by_pipe(tokens):
    commands = []
    current_command = []

    current_token = 0
    while current_token < len(tokens):
        if tokens[current_token] == "=":
            current_command.append('assign')
            current_command.append(tokens[current_token - 1])
            current_command.append(tokens[current_token + 1])
            current_token += 1
        elif tokens[current_token] == "|":
            if current_command:
                commands.append(current_command)
            current_command = []
        else:
            if current_token == len(tokens) - 1:
                current_command.append(tokens[current_token])
            elif current_token < len(tokens) - 1:
                if tokens[current_token + 1] != "=":
                    current_command.append(tokens[current_token])
        current_token += 1

    if current_command:
        commands.append(current_command)

    return commands
