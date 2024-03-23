#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define ROWS 10
#define COLS 30

// Función para imprimir el tablero
void print_board(char board[ROWS][COLS]) {
    system("clear");
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            printf("%c", board[i][j]);
        }
        printf("\n");
    }
}

// Función para contar vecinos vivos
int count_neighbors(char board[ROWS][COLS], int row, int col) {
    int count = 0;
    for (int i = row - 1; i <= row + 1; i++) {
        for (int j = col - 1; j <= col + 1; j++) {
            if (i >= 0 && i < ROWS && j >= 0 && j < COLS && (i != row || j != col)) {
                if (board[i][j] == '*') {
                    count++;
                }
            }
        }
    }
    return count;
}

// Función para calcular el siguiente estado del tablero
void next_generation(char board[ROWS][COLS]) {
    char new_board[ROWS][COLS];
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            int neighbors = count_neighbors(board, i, j);
            if (board[i][j] == '*') {
                if (neighbors < 2 || neighbors > 3) {
                    new_board[i][j] = ' ';
                } else {
                    new_board[i][j] = '*';
                }
            } else {
                if (neighbors == 3) {
                    new_board[i][j] = '*';
                } else {
                    new_board[i][j] = ' ';
                }
            }
        }
    }
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            board[i][j] = new_board[i][j];
        }
    }
}

int main() {
    char board[ROWS][COLS];

    // Inicializar el tablero aleatoriamente
    for (int i = 0; i < ROWS; i++) {
        for (int j = 0; j < COLS; j++) {
            board[i][j] = rand() % 2 ? '*' : ' ';
        }
    }

    // Ciclo principal del juego
    while (1) {
        print_board(board);
        usleep(200000); // Pausa de 200ms
        next_generation(board);
    }

    return 0;
}
