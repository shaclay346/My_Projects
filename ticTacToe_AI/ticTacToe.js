
let scores = {
    //human starts as x
    X: -1,
    //ai is O so they should have the highest score 
    O: 1,
    tie: 0
};



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
                // without this the AI would make unnessary moves (it would still win the game with those moves) but would
                //make a move that results in more future wins, rather than just winning faster.
                if (checkWinner() == 'O') {
                    board[i][j].innerHTML = '<p>O</p>';
                    displayWinner('O');
                    return;
                }

                //call minimax to find the best possible move
                let score = minimax(board, false);

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
    } winner = checkWinner();
    //have the computer play the best move
    if (winner == null) {
        board[move.i][move.j].innerHTML = '<p>O</p>';
    }
    else {
        displayWinner(winner);
    }
}

//minimax to fill out the full board for all potenetial AI moves
//and returns if the AI wins that game
function minimax(board, isAiTurn) {
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
const AiButton = document.getElementById('AI');

AiButton.addEventListener('click', function (e) {
    newGame();
    board = getBoard();
    board[0][0].innerHTML = '<p>O</p>';

});



const s = document.getElementById('container');

//add a listener to all squares 
s.addEventListener('click', function (e) {

    if (e.target.className == 'square') {
        const square = e.target

        winner = checkWinner();
        if (winner == null) {
            square.innerHTML = '<p>X</p>';
            console.log("called best move");
            bestMove(getBoard());
        }
        else {
            displayWinner();
        }

    }

});


const newButton = document.getElementById('reset');
newButton.addEventListener('click', function (e) {
    newGame();
});



