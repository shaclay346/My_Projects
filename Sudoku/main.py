# i made the board from a sudoku board from the internet
board = [
    [5, 3, 0, 0, 7, 0, 0, 0, 0],
    [6, 0, 0, 1, 9, 5, 0, 0, 0],
    [0, 9, 8, 0, 0, 0, 0, 6, 0],
    [8, 0, 0, 0, 6, 0, 0, 0, 3],
    [4, 0, 0, 8, 0, 3, 0, 0, 1],
    [7, 0, 0, 0, 2, 0, 0, 0, 6],
    [0, 6, 0, 0, 0, 0, 2, 8, 0],
    [0, 0, 0, 4, 1, 9, 0, 0, 5],
    [0, 0, 0, 0, 8, 0, 0, 7, 9],
]


# print the sudoku board
def print_board(board):
    for i in range(len(board)):
        # if (i % 3 == 0 and i != 0):
        if (i % 3 == 0):
            print(" - - - - - - - - - -")
        for j in range(len(board)):
            if j == 0:
                print("|", end="")
            if j == 8:
                print(board[i][j], "|")
            else:
                print(str(board[i][j]) + " ", end="")

                # if((j + 1)% 3 == 0 and j != 0):
                if (j + 1) % 3 == 0:
                    print("|", end="")
        if (i % 8 == 0 and i != 0):
            print(" - - - - - - - - - -")

#finds any blank spave in the board denoted by 0
def find_zero(board):
    #simply loop through the board until a 0 is found and return that index
    for i in range(len(board)):
        for j in range(len(board[0])):
            if (board[i][j] == 0):
                return (i, j)  # we are returning row then col  or y then x
    # return none once there are no 0s left on the board
    return None

#given a position in the board return true if it is valid, else return false
def is_valid(board, number, pos):
    # check row
    for i in range(len(board[0])):
        if board[pos[0]][i] == number:
            return False

    # check the column
    for i in range(len(board)):
        if board[i][pos[1]] == number:
            return False

    # check square
    box_row = pos[0] // 3
    box_col = pos[1] // 3
    # make sure we don't have the same element twice in a box
    for i in range(box_row * 3, box_row * 3 + 3):
        for j in range(box_col * 3, box_col * 3 + 3):
            if board[i][j] == number and (i, j) != pos:
                return False
    #if the row, column and square are all valid solutions then return true that is a valid move
    return True


def solve(board):
    zero = find_zero(board)
    #base case
    if zero == None:
        return True
    #else value to
    else:
        #set the empty space to an index of the list so I can call is_valid
        row, col = zero

    for i in range(1, 10):
        #check if adding is valid
        if is_valid(board, i, (row, col)):
            #add to the board
            board[row][col] = i

            #recusively try to finish the board until the board is finished
            if solve(board):
                return True

            #back track the current solution can't be finished with what was added
            board[row][col] = 0

    return False



print("\nunsolved sudoku board:\n")
print_board(board)


print("\nafter the call we have this board:\n")
solve(board)
print_board(board)
