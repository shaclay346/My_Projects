
let scores = {
    //human starts as x
    X: -1,
    //ai is O so they should have the highest score 
    O: 1,
    tie: 0
};
let isAITurn = false
const turnText = document.getElementById("turn")
const winnerText = document.getElementById('message');

//helper function to determine if three squares all hold the same value
function allEqual(spot1, spot2, spot3) {
    if (spot1.innerText == spot2.innerText && spot2.innerText == spot3.innerText && spot1.innerText != '') {
        return true;
    }
    return false;
}

//returns true if there are any open spots remaining
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

//returns true if the board is empty, else returns false
function isBoardEmpty(board) {
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            if (board[i][j].innerText != '') {
                return false;
            }
        }
    }
    return true;
}

function getBoard() {
    const squares = document.querySelectorAll('.square');

    //2d array to represent the board
    let board = [
        [squares[0], squares[1], squares[2]],
        [squares[3], squares[4], squares[5]],
        [squares[6], squares[7], squares[8]]
    ]

    return board;
}

//determines and play the best possible move for the AI
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

                //if the AI can win just make the winning play and don't even bother calling minimax 
                // without this the AI would make unnecessary moves (it would still win the game with those moves) but would
                //make a move that results in more future wins, rather than just winning faster.
                if (checkWinner() == 'O') {
                    board[i][j].innerHTML = '<p>O</p>';
                    displayWinner('O');
                    return;
                }

                //call minimax to find the best possible move
                let score = minimax(board, false);
                isAITurn = false

                board[i][j].innerHTML = '';
                //if the score returned is higher than high score, update high score
                //whichever score is the highest, is the best possible move that results in 
                //the most wins for the AI
                if (score > highScore) {
                    //console.log("enter here");
                    highScore = score;
                    move = { i, j };
                }
            }
        }
    }
    winner = checkWinner();
    //have the computer play the best move
    if (winner == null) {
        board[move.i][move.j].innerHTML = '<p>O</p>';
        //if the previous move was the winning play check winner again
        winner = checkWinner();
        if (winner != null) {
            displayWinner(winner);
        }
    }
    else {
        displayWinner(winner);
    }
}


//to save on run time, I hard coded the first move of the AI
//it is still playing the most efficient move, just not doing any
//calculation since the first move was taking upwards of 5 seconds
//to play
function determineAIFirstMove(board) {
    if (board[0][0].innerHTML != '' || board[0][2].innerHTML != '' ||
        board[2][0].innerHTML != '' || board[2][2].innerHTML != '') {
        board[1][1].innerHTML = '<p>O</p>';
    }
    else if (board[1][2].innerHTML != '') {
        board[0][2].innerHTML = '<p>O</p>';
    }
    else if (board[2][1].innerHTML != '') {
        board[0][1].innerHTML = '<p>O</p>';
    }
    else {
        board[0][0].innerHTML = '<p>O</p>';
    }
}


//minimax to fill out the full board for all potential AI moves
//and returns if the AI wins that game
function minimax(board, isAiTurn) {
    let result = checkWinner();

    //if there was winner or a tie 
    if (result !== null) {
        //return the value associated with the board
        return scores[result];
    }

    if (isAiTurn) {
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
        return highScore;
    } else {
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
        return highScore;
    }
}

function displayWinner(winner) {
    turnText.innerText = ""
    //if board is full and no one wins 
    if (winner == 'tie') {
        const message = document.getElementById('message');
        message.innerText = "Tie!";
    }
    //else have some winning animation happen 
    else {
        const message = document.getElementById('message');
        message.innerText = `${winner} Wins!`;
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
    //then someone won the game

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

//wipes the board and starts a new game with the User going first
function newGame() {
    const squares = document.querySelectorAll('.square');

    squares.forEach(item => {
        //remove the html to reset board
        item.innerHTML = '';
    });

    winnerText.innerText = '';
    isAITurn = false
    turnText.innerText = 'Your turn!'
}


const AiButton = document.getElementById('AI');
AiButton.addEventListener('click', () => {
    newGame();
    determineAIFirstMove(getBoard())
});


if (!isAITurn) {
    turnText.innerText = "Your turn!"
}
if (isAITurn) {
    turnText.innerText = ""
}



const s = document.getElementById('container');
//add a listener to all squares 
s.addEventListener('click', function (e) {
    if (e.target.className === 'square') {
        isAITurn = true
        const square = e.target

        winner = checkWinner();
        if (!!winner) {
            displayWinner();
        }
        else {
            if (isBoardEmpty(getBoard())) {
                square.innerHTML = '<p>X</p>';
                determineAIFirstMove(getBoard());
            }
            else {
                square.innerHTML = '<p>X</p>';
                bestMove(getBoard());
            }
        }

    }

});


const newButton = document.getElementById('reset');
newButton.addEventListener('click', function (e) {
    newGame();
});



