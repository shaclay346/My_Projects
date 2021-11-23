
//init();
let scores = {
    //human starts as x
    X: -1,
    //ai is O so they should have the highest score 
    O: 1,
    tie: 0
};



// function selectO() {
//     const oButton = document.querySelector('.mode');
//     const xButton = document.querySelector('.mode.selected');

//     xButton.classList.remove('selected');
//     oButton.classList.add('selected');
// }


// function selectX() {
//     const buttons = document.querySelectorAll('.mode');

//     buttons[0].classList.remove('selected');
//     buttons[1].classList.add('selected');
// }

// function getNum(square) {
//     const squares = document.querySelectorAll('.square');
//     for (let i = 0; i < squares.length; i++) {
//         if (squares[i] == square) {
//             return i;
//         }
//     }
// }

function allEqual(spot1, spot2, spot3) {
    if (spot1.innerText == spot2.innerText && spot2.innerText == spot3.innerText && spot1.innerText != '') {
        return true;
    }
    return false;
}

function openSpots(board) {
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (board[i][j].innerText == '') {
                return true;
            }
        }
    }
    return false;
}

function getBoard() {
    const squares = document.querySelectorAll('.square');

    //make the squares into a 2d array
    let board = [
        [squares[0], squares[1], squares[2]],
        [squares[3], squares[4], squares[5]],
        [squares[6], squares[7], squares[8]]
    ]

    return board;
}

function printBoard(board) {
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            console.log(board[i][j].innerHTML);
        }
    }
}



function bestMove(board) {
    // AI to make its turn
    let highScore = -Infinity;
    let move;
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            // if the spot is empty
            if (board[i][j].innerText == '') {
                //play for the AI at every spot
                board[i][j].innerText = 'O';

                let score = minimax(board, false, 0);

                board[i][j].innerHTML = '';
                //if the score is higher than best score
                if (score > highScore) {
                    //console.log("enter here");
                    highScore = score;
                    move = { i, j };
                }
            }
        }
    }
    //have the computer play the best move
    board[move.i][move.j].innerHTML = '<p>O</p>';
    win = checkWinner();
    if (win != null) {
        displayWinner();
    }
}

//keep track of number of wins for given score
//or don't search entire game, just a couple moves ahead 
function minimax(board, isAiTurn, numberOfWins) {
    let result = checkWinner();

    //if there was winner or a tie 
    if (result !== null) {
        //return the value associated with the board
        return scores[result];
    }

    if (isAiTurn) {
        // console.log("AI turn");
        let highScore = -Infinity;

        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                // if the spot is empty
                if (board[i][j].innerText == '') {
                    //ai
                    board[i][j].innerText = 'O';
                    let score = minimax(board, false);
                    board[i][j].innerText = '';
                    if (score > highScore) {
                        highScore = score;
                    }
                }
            }
        }
        //error could be here
        // console.log("best score is top: ", highScore);
        return highScore;
    } else {
        //console.log("human turn");
        let highScore = Infinity;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                // if the spot is empty
                if (board[i][j].innerText == '') {
                    //player
                    board[i][j].innerText = 'X';
                    let score = minimax(board, true);
                    board[i][j].innerText = '';
                    if (score < highScore) {
                        highScore = score;
                    }
                }
            }
        }
        // console.log("best score is bottom: ", highScore);
        return highScore;
    }
}

function displayWinner(winner) {
    //if board is full and no one wins 
    if (winner == 'tie') {
        const message = document.getElementById('message');
        message.innerText = "Tie!";
    }
    //else have some winning animation happen 
    else {
        const message = document.getElementById('message');
        message.innerText = "Winner!";
    }
}

//after a player makes their turn I use this method to determine
//if someone won the game
function checkWinner() {
    // get the index of the square they clicked 
    const squares = document.querySelectorAll('.square');
    let winner = null;

    let board = [
        [squares[0], squares[1], squares[2]],
        [squares[3], squares[4], squares[5]],
        [squares[6], squares[7], squares[8]]
    ]
    //check every line to see if there is a winner
    //if a straight line on the board has three of the same character
    //then someone one the game

    //check all horizontal spots
    for (let i = 0; i < 3; i++) {
        if (allEqual(board[i][0], board[i][1], board[i][2])) {
            return board[i][0].innerText;
        }
    }

    //check vertical spots 
    for (let i = 0; i < 3; i++) {
        if (allEqual(board[0][i], board[1][i], board[2][i])) {
            return board[0][i].innerText;
        }
    }

    //diagonal spots
    if (allEqual(board[0][0], board[1][1], board[2][2])) {
        return board[1][1].innerText;
    }

    if (allEqual(board[0][2], board[1][1], board[2][0])) {
        return board[1][1].innerText;
    }

    //if there is no winner and board is full 
    if (winner == null && openSpots(board) == false) {
        return 'tie';
    }
    else {
        return null;
    }
}


function newGame() {
    const squares = document.querySelectorAll('.square');

    squares.forEach(item => {
        //remove the html to reset board
        item.innerHTML = '';
    });

    const text = document.getElementById('message');
    text.innerText = '';
}


//function init() {

const s = document.getElementById('container');


let counter = 0;
//bestMove(getBoard());
//add a listener to all squares 
s.addEventListener('click', function (e) {

    if (e.target.className == 'square') {
        const square = e.target
        counter++;

        square.innerHTML = '<p>X</p>';

        win = checkWinner();
        if (win == null) {
            console.log("called best move");
            bestMove(getBoard());
        }
        else {
            displayWinner(win);
        }

    }

});


const newButton = document.getElementById('reset');
newButton.addEventListener('click', function (e) {
    newGame();
    // selectX();
    if (counter % 2 == 1) {
        counter++;
    }
});

//}





