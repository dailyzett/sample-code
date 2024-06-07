const React = require('react');
const {createRef, useState} = require("react");

function NumberBaseball() {
    const generateNumbers = () => {
        let numbers = [];
        while (numbers.length < 4) {
            let rand = Math.floor(Math.random() * 9) + 1;
            if (numbers.indexOf(rand) === -1) numbers.push(rand);
        }
        return numbers;
    }

    const [randomNumbers, setRandomNumbers] = useState(generateNumbers);
    const [value, setValue] = useState('');
    const [history, setHistory] = useState([]);
    const [ball, setBall] = useState(0);
    const [strike, setStrike] = useState(0);
    const [isOut, setIsOut] = useState(false);
    const [life, setLife] = useState(10);

    const checkNumbers = () => {
        const inputNumbers = value.split('').map(Number);
        const numbers = randomNumbers;

        let newStrike = 0;
        let newBall = 0;
        let isNewOut = false;

        for (let i = 0; i < inputNumbers.length; i++) {
            if (inputNumbers[i] === numbers[i]) {
                newStrike += 1;
            } else if (inputNumbers.includes(numbers[i])) {
                newBall += 1;
            }
        }

        setStrike(newStrike);
        setBall(newBall);
        isNewOut = newStrike === 0 && newBall === 0;
        setIsOut(isNewOut);
        setHistory(prevHistory => [...prevHistory, {
            value: inputNumbers,
            strike: newStrike,
            ball: newBall,
            isOut: isNewOut
        }]);
    }

    function handleSubmit(e) {
        if (value.length < 4) return;
        e.preventDefault();
        if (life === 0 || strike === 4) {
            setHistory([]);
            setLife(10);
            setStrike(0);
            setBall(0);
            setRandomNumbers(generateNumbers);
        } else {
            checkNumbers();
            setLife(life - 1);
        }
        setValue('');
    }

    function handleChange(e) {
        setValue(e.target.value);
    }

    return (
        <>
            <div>
                <div>남은 기회: {life}</div>
                <form onSubmit={handleSubmit}>
                    <input value={value} onChange={handleChange}/>
                    <button>입력</button>
                </form>
            </div>
            <div>
                <ul>
                    {history.map((item, index) => (
                        <li key={index}>{item.value}&nbsp;
                            <b>스트라이크: {item.strike}</b> 볼: {item.ball} {item.isOut ? '아웃!' : ''}
                        </li>
                    ))}
                </ul>
            </div>
            <div>{life === 0 || strike === 4 ? `정답: ${randomNumbers}` : ''}</div>
        </>
    )
}

module.exports = NumberBaseball;