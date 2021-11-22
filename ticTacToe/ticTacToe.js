
//init();
let scores = {
    X: 10,
    O: -10,
    tie: 0
};



function selectO() {
    const oButton = document.querySelector('.mode');
    const xButton = document.querySelector('.mode.selected');

    xButton.classList.remove('selected');
    oButton.classList.add('selected');
}


function selectX() {
    const buttons = document.querySelectorAll('.mode');

    buttons[0].classList.remove('selected');
    buttons[1].classList.add('selected');
}

function getNum(square) {
    const squares = document.querySelectorAll('.square');
    for (let i = 0; i < squares.length; i++) {
        if (squares[i] == square) {
            return i;
        }
    }
}

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

    let board = [
        [squares[0], squares[1], squares[2]],
        [squares[3], squares[4], squares[5]],
        [squares[6], squares[7], squares[8]]
    ]

    return board;
}

function bestMove(board) {
    // AI to make its turn
    let bestScore = -Infinity;
    let move;
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            // if the spot is empty
            if (board[i][j].innerText == '') {
                board[i][j].innerHTML == '<p>O</p>';

                let score = minimax(board, 0, false);

                board[i][j].innerHTML = '';
                //not getting into here score is not correct
                if (score > bestScore) {
                    console.log("enter here");
                    bestScore = score;
                    move = { i, j };
                }
            }
        }
    }
    //console.log(move.i, move.j);
    board[move.i][move.j].innerText = '<p>O</p>';
}


function minimax(board, depth, isMaximizing) {
    let result = checkWinner();

    //if there was winner or a tie 
    if (result !== null) {
        //return the value associated with the board
        return scores[result];
    }

    if (isMaximizing) {
        console.log("maximizing");
        let bestScore = -Infinity;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                // Is the spot available?
                if (board[i][j].innerText == '') {
                    board[i][j].innerHTML = '<p>X</p>';
                    let score = minimax(board, depth + 1, false);
                    board[i][j].innerText = '';
                    bestScore = Math.max(score, bestScore);
                }
            }
        }
        console.log("best score is: ", bestScore);
        return bestScore;
    } else {
        console.log("minimizing");
        let bestScore = Infinity;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                // Is the spot available?
                if (board[i][j].innerText == '') {
                    board[i][j].innerHTML = 'O';
                    let score = minimax(board, depth + 1, true);
                    board[i][j].innerText = '';
                    bestScore = Math.min(score, bestScore);
                }
            }
        }
        console.log("best score is minimizing: ", bestScore);
        return bestScore;
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
            const message = document.getElementById('message');
            message.innerText = "Winner!";
            return board[i][0].innerText;
        }
    }

    //check vertical spots 
    for (let i = 0; i < 3; i++) {
        if (allEqual(board[0][i], board[1][i], board[2][i])) {
            const message = document.getElementById('message');
            message.innerText = "Winner!";
            return board[0][i].innerText;
        }
    }

    //diagonal spots
    if (allEqual(board[0][0], board[1][1], board[2][2])) {
        const message = document.getElementById('message');
        message.innerText = "Winner!";
        return board[1][1].innerText;
    }

    if (allEqual(board[0][2], board[1][1], board[2][0])) {
        const message = document.getElementById('message');
        message.innerText = "Winner!";
        return board[1][1].innerText;
    }


    //if there is no winner and board is full 
    if (winner == null && openSpots(board) == false) {
        console.log("tie at bottom");
        const message = document.getElementById('message');
        message.innerText = "Tie";
        return 'tie';
    }
    else {
        console.log('RETURNED NULL NO TIE OR WIN')
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

        if (checkWinner() == null) {
            bestMove(getBoard());
        }

    }

});


const newButton = document.getElementById('reset');
newButton.addEventListener('click', function (e) {
    newGame();
    selectX();
    if (counter % 2 == 1) {
        counter++;
    }
});

//}





