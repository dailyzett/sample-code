const React = require('react');
const {useState, useEffect, useRef, useMemo, useCallback} = require("react");
const Ball = require('./Ball.jsx');

function getWinNumbers() {
    const candidate = Array(45).fill(0).map((v, i) => i + 1);
    const shuffle = [];

    while (candidate.length > 0) {
        shuffle.push(candidate.splice(Math.floor(Math.random() * candidate.length), 1)[0]);
    }
    const bonusNumber = shuffle[shuffle.length - 1];
    const winNumbers = shuffle.slice(0, 6).sort((a, b) => a - b);
    return [...winNumbers, bonusNumber]
}

function Lotto() {
    const lottoNumber = useMemo(() => getWinNumbers(), []);
    const [winNumbers, setWinNumbers] = useState(lottoNumber);
    const [winBalls, setWinBalls] = useState([]);
    const [bonusNumber, setBonusNumber] = useState(null);
    const [redo, setRedo] = useState(false);
    const timeOut = useRef([]);

    useEffect(() => {
        for (let i = 0; i < winNumbers.length - 1; i++) {
            timeOut.current[i] = setTimeout(() => {
                setWinBalls((prevBalls) => [...prevBalls, winNumbers[i]]);
            }, (i + 1) * 1000);
        }
        timeOut.current[6] = setTimeout(() => {
            setBonusNumber(winNumbers[6]);
            setRedo(true);
        }, 7000);

        return () => {
            timeOut.current.forEach((v) => {
                clearTimeout(v)
            })
        }
    }, [timeOut.current]);

    const onClickRedo = useCallback(() => {
        setWinNumbers(getWinNumbers());
        setWinBalls([]);
        setRedo(false);
        setBonusNumber(null);
        timeOut.current = [];
    }, []);

    return (
        <>
            <div>당첨 숫자</div>
            <div id="결과창">
                {winBalls.map((v) => <Ball key={v} number={v}/>)}
            </div>
            <div>보너스!!</div>
            {bonusNumber && <Ball number={bonusNumber}/>}
            {redo && <button onClick={onClickRedo}>한번 더!</button>}
        </>
    )
}

module.exports = Lotto;