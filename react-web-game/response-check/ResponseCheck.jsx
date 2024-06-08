const React = require('react');
const {createRef, useState, useRef} = require("react");

function ResponseCheck() {
    const [state, setState] = useState('waiting');
    const [message, setMessage] = useState('클릭해서 시작하세요.');
    const [result, setResult] = useState([]);

    const changeStatusToNow = () => {
        setState('now')
        setMessage('지금!');
        startTime.current = new Date();
    }

    const changeStatusToWaiting = (newResult) => {
        setState('waiting');
        setMessage('클릭해서 시작하세요.');
        setResult([...result, newResult]);
    }

    const timer = useRef(null);
    const startTime = useRef(null);
    const endTime = useRef(null);

    const handleClick = () => {
        if (state === 'waiting') {
            setState('ready');
            setMessage('초록색이 되면 클릭하세요');
            timer.current = setTimeout(changeStatusToNow, Math.floor(Math.random() * 1000) + 2000);
        } else if (state === 'now') {
            endTime.current = new Date();
            changeStatusToWaiting(endTime.current - startTime.current);
        } else if (state === 'ready') {
            if (timer.current) clearTimeout(timer.current);
            setState('waiting');
            setMessage('너무 성급하시네요');
            setResult([]);
        }
    };

    const resetClick = () => {
        setResult([]);
    }

    return (
        <>
            <div id="screen" className={state} onClick={handleClick}>{message}</div>
            <div>
                {result.length === 0
                    ? null
                    : `평균 시간: ${result.reduce((a, c) => a + c) / result.length}ms`}
            </div>
            <button onClick={resetClick}>리셋</button>
        </>
    )
}

module.exports = ResponseCheck;