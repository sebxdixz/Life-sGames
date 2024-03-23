#!/bin/bash

# Tamaño del tablero
ROWS=10
COLS=30

# Función para imprimir el tablero
print_board() {
    clear
    for ((i = 0; i < ROWS; i++)); do
        for ((j = 0; j < COLS; j++)); do
            echo -n "${board[$i,$j]}"
        done
        echo
    done
}

# Función para inicializar el tablero
initialize_board() {
    for ((i = 0; i < ROWS; i++)); do
        for ((j = 0; j < COLS; j++)); do
            board[$i,$j]=' '
        done
    done
}

# Función para generar el tablero inicial aleatorio
randomize_board() {
    for ((i = 0; i < ROWS; i++)); do
        for ((j = 0; j < COLS; j++)); do
            rand=$((RANDOM % 2))
            if [[ $rand -eq 1 ]]; then
                board[$i,$j]='*'
            else
                board[$i,$j]=' '
            fi
        done
    done
}

# Función para contar vecinos vivos
count_neighbors() {
    local row=$1
    local col=$2
    local count=0

    for ((i = row - 1; i <= row + 1; i++)); do
        for ((j = col - 1; j <= col + 1; j++)); do
            if [[ $i -ge 0 && $i -lt ROWS && $j -ge 0 && $j -lt COLS && ($i -ne $row || $j -ne $col) ]]; then
                if [[ ${board[$i,$j]} == '*' ]]; then
                    ((count++))
                fi
            fi
        done
    done

    echo $count
}

# Función para calcular el siguiente estado del tablero
next_generation() {
    declare -A new_board
    for ((i = 0; i < ROWS; i++)); do
        for ((j = 0; j < COLS; j++)); do
            neighbors=$(count_neighbors $i $j)
            if [[ ${board[$i,$j]} == '*' ]]; then
                if [[ $neighbors -lt 2 || $neighbors -gt 3 ]]; then
                    new_board[$i,$j]=' '
                else
                    new_board[$i,$j]='*'
                fi
            else
                if [[ $neighbors -eq 3 ]]; then
                    new_board[$i,$j]='*'
                else
                    new_board[$i,$j]=' '
                fi
            fi
        done
    done
    board=("${new_board[@]}")
}

# Inicializar y generar el tablero
initialize_board
randomize_board
print_board

# Ciclo principal del juego
while true; do
    read -s -n 1 -t 1 key
    if [[ $key == 'q' ]]; then
        break
    fi
    next_generation
    print_board
done
