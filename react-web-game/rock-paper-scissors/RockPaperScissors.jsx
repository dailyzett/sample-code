const React = require('react');
const {createRef, useRef, useEffect} = require("react");
const useInterval = require('./useInterval');

const rspCoords = {
    바위: '0',
    가위: '-142px',
    보: '-284px',
};

const scores = {
    가위: 1,
    바위: 0,
    보: -1,
};

const computerChoice = (imgCoord) => {
    return Object.entries(rspCoords).find(function (v) {
        return v[1] === imgCoord;
    })[0];
};

function RockPaperScissors() {
    const [imgCoord, setImgCoord] = React.useState(0);
    const [result, setResult] = React.useState(rspCoords.바위);
    const [score, setScore] = React.useState(0);
    const [isRunning, setIsRunning] = React.useState(true);

    const changeHand = () => {
        if (imgCoord === rspCoords.바위) return setImgCoord(rspCoords.가위);
        else if (imgCoord === rspCoords.가위) return setImgCoord(rspCoords.보);
        else return setImgCoord(rspCoords.바위);
    }

    useInterval(changeHand, isRunning ? 100 : null);

    const onClickBtn = (choice) => () => {
        if (isRunning) {
            setIsRunning(false);

            const userHand = scores[choice];
            const computerHand = scores[computerChoice(imgCoord)];
            const diff = Number(userHand) - Number(computerHand);
            if (diff === 0) setResult('비겼습니다.');
            else if ([-1, 2].includes(diff)) {
                setResult('이겼습니다.');
                setScore(score + 1);
            } else {
                setResult("졌습니다.");
                setScore(score - 1);
            }

            setTimeout(() => {
                setIsRunning(true);
            }, 1000);
        }
    };

    return (
        <>
            <div id="computer"
                 style={{background: `url(https://en.pimg.jp/023/182/267/1/23182267.jpg) ${imgCoord} 0`}}/>
            <div>
                <button id="rock" className="btn" onClick={onClickBtn('바위')}>바위</button>
                <button id="scissor" className="btn" onClick={onClickBtn('가위')}>가위</button>
                <button id="paper" className="btn" onClick={onClickBtn('보')}>보</button>
            </div>
            <div>{result}</div>
            <div>현재 {score}점</div>
        </>
    );
}

module.exports = RockPaperScissors;