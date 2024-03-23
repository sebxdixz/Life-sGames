import numpy as np
import os
import time

def print_board(board):
    os.system('cls')
    for row in board:
        print(''.join(row))
    print()

def count_neighbors(board, row, col):
    count = 0
    for i in range(row-1, row+2):
        for j in range(col-1, col+2):
            if i != row or j != col:
                count += board[(i % len(board)), (j % len(board[0]))] == '*'
    return count

def next_generation(board):
    new_board = np.copy(board)
    for i in range(len(board)):
        for j in range(len(board[0])):
            neighbors = count_neighbors(board, i, j)
            if board[i, j] == '*':
                if neighbors < 2 or neighbors > 3:
                    new_board[i, j] = ' '
            else:
                if neighbors == 3:
                    new_board[i, j] = '*'
    return new_board

# Tamaño del tablero
ROWS = 30
COLS = 30

# Inicializar tablero aleatoriamente
board = np.random.choice([' ', '*'], size=(ROWS, COLS))

# Ciclo principal del juego
while True:
    print_board(board)
    time.sleep(0.2)  # Pausa de 0.2 segundos entre generaciones
    board = next_generation(board)
