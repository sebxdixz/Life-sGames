// Función para imprimir el tablero
function printBoard(board) {
    console.clear();
    for (let row of board) {
        console.log(row.join(''));
    }
    console.log();
}

// Función para contar vecinos vivos
function countNeighbors(board, row, col) {
    let count = 0;
    for (let i = row - 1; i <= row + 1; i++) {
        for (let j = col - 1; j <= col + 1; j++) {
            if (i !== row || j !== col) {
                if (board[i] && board[i][j] === '*') {
                    count++;
                }
            }
        }
    }
    return count;
}

// Función para calcular el siguiente estado del tablero
function nextGeneration(board) {
    const newBoard = [];
    for (let i = 0; i < board.length; i++) {
        const newRow = [];
        for (let j = 0; j < board[i].length; j++) {
            const neighbors = countNeighbors(board, i, j);
            if (board[i][j] === '*') {
                if (neighbors < 2 || neighbors > 3) {
                    newRow.push(' ');
                } else {
                    newRow.push('*');
                }
            } else {
                if (neighbors === 3) {
                    newRow.push('*');
                } else {
                    newRow.push(' ');
                }
            }
        }
        newBoard.push(newRow);
    }
    return newBoard;
}

// Tamaño del tablero
const ROWS = 10;
const COLS = 30;

// Inicializar tablero aleatoriamente
const board = Array.from({ length: ROWS }, () =>
    Array.from({ length: COLS }, () => (Math.random() > 0.5 ? '*' : ' '))
);

// Ciclo principal del juego
setInterval(() => {
    printBoard(board);
    const newBoard = nextGeneration(board);
    for (let i = 0; i < board.length; i++) {
        for (let j = 0; j < board[i].length; j++) {
            board[i][j] = newBoard[i][j];
        }
    }
}, 200);
