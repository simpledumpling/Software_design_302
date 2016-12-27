import re


# It is a supporting function, which find in input string substring that match regular expression and return it
# We'll need it in substitution and tokenizing
def match(regular_expr, input_string):
    match_result = re.match(regular_expr, input_string)
    content = match_result.group()

    return content


# First we need to substitute variables, where it is necessary. We find '$' symbol and replace variable name after it to
# variable value, if this variable contains in environment.

# For matching variable name we use regular expressions: variable name may include letters, numbers, '_' and '-' symbols
def substitute_variables(input_stream, environment):
    is_single_quotes_open = False
    current_position = 0

    while current_position < len(input_stream):
        if input_stream[current_position] == "'":
            is_single_quotes_open = not is_single_quotes_open

        elif input_stream[current_position] == '$' and not is_single_quotes_open:
            input_for_varname_match = input_stream[current_position + 1:]
            variable_name = match(r'(\w|\d|[-,_])*', input_for_varname_match)

            if variable_name in environment:
                new_current_position = current_position + len(environment[variable_name]) - 1
                input_stream = input_stream[:current_position] + str(environment[variable_name]) \
                               + input_stream[current_position + len(variable_name) + 1:]
                current_position = new_current_position

        current_position += 1

    return input_stream


# After substitution we need to tokenize our command string
def tokenize_command_string(input_stream, environment):
    input_stream = substitute_variables(input_stream, environment)
    tokens = []

    current_token = 0

    while current_token < len(input_stream):
        if input_stream[current_token] == "\"":
            input_for_quotes_match = input_stream[current_token + 1:]
            token = match(r'[^"]*', input_for_quotes_match)
            current_token = current_token + len(token) + 2
        elif input_stream[current_token] == "'":
            input_for_quotes_match = input_stream[current_token + 1:]
            token = match(r"[^']*", input_for_quotes_match)
            current_token = current_token + len(token) + 2
        elif input_stream[current_token] == "=":
            token = "="
            current_token += 1
        elif input_stream[current_token] == "|":
            token = "|"
            current_token += 1
        else:
            input_for_text_match = input_stream[current_token:]
            token = match(r'[^\s,|,=]*', input_for_text_match)
            if len(token) != 0:
                current_token += len(token)
            else:
                current_token += 1

        if token != '':
            tokens.append(token)

    return tokens


def update_environment(tokens, environment):
    token_id = 0
    while token_id < len(tokens) - 1:
        if tokens[token_id] == "=":
            name = tokens[token_id - 1]
            value = tokens[token_id + 1]
            environment[name] = value
        token_id += 1
