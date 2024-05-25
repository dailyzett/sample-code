import './App.css'
import {useState} from "react";

function Square({value, onSquareClick}) {
    return <button className="square" onClick={onSquareClick}>{value}</button>;
}

function Board({xIsNext, squares, onPlay}) {
    const winner = calculateWinner(squares);
    let status;
    if (winner) {
        status = `Winner ${winner}`
    } else {
        status = `Next Player: ${xIsNext ? 'X' : 'O'}`;
    }

    function handleClick(i) {
        if (squares[i] || calculateWinner(squares)) return;
        let nextSquares = squares.slice();
        if (xIsNext) nextSquares[i] = 'X';
        else nextSquares[i] = 'O';
        onPlay(nextSquares);
    }

    return (
        <>
            <div className="status">{status}</div>
            <div>
                {[0, 1, 2].map(i => (
                    <div className="board-row" key={i}>
                        {squares.slice(i * 3, i * 3 + 3).map((value, j) => (
                            <Square key={j} value={value} onSquareClick={() => handleClick(i * 3 + j)}/>
                        ))}
                    </div>
                ))}
            </div>
        </>
    );
}

export default function Game() {
    const [history, setHistory] = useState([Array(9).fill(null)]);
    const [currentMove, setCurrentMove] = useState(0);
    const currentSquares = history[currentMove];
    const xIsNext = currentMove % 2 === 0;

    function handlePlay(nextSquares) {
        const nextHistory = [...history.slice(0, currentMove + 1), nextSquares];
        setHistory(nextHistory);
        setCurrentMove(nextHistory.length - 1);
    }

    function jumpTo(nextMove) {
        setCurrentMove(nextMove);
    }

    const moves = history.map((squares, index) => {
            let description
            if (index === 0) {
                description = 'Go to Game Start';
            } else {
                description = `Go to move #${index}`;
            }

            return (
                <li key={index}>
                    <button onClick={() => jumpTo(index)}>{description}</button>
                </li>
            )
        }
    );

    return (
        <div className="game">
            <div className="game-board">
                <Board xIsNext={xIsNext} squares={currentSquares} onPlay={handlePlay}/>
            </div>
            <div className="game-info">
                <ol>{moves}</ol>
            </div>
        </div>
    )
}

function calculateWinner(squares) {
    const lines = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6]
    ];

    for (let i = 0; i < lines.length; i++) {
        const [a, b, c] = lines[i];
        if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
            return squares[a];
        }
    }
    return null;
}
