const React = require('react');
const {createRef} = require("react");

function WorldRelay() {
    const [prev, setPrev] = React.useState('정발산');
    const [next, setNext] = React.useState('');
    const [result, setResult] = React.useState('정발산');
    const inputRef = createRef();

    function handleChange(e) {
        setNext(e.target.value);
    }

    function handleClick() {
        const lastChar = prev[prev.length - 1];
        const firstChar = next.at(0);

        if (lastChar === firstChar) {
            setPrev(next);
            setResult(next);
        }
        setNext('');
        inputRef.current.focus();
    }

    function handleKeyPress(e) {
        if (e.key === 'Enter') {
            handleClick();
        }
    }

    return (
        <>
            <div>{result}</div>
            <input value={next} onChange={handleChange} onKeyDown={handleKeyPress} ref={inputRef}/>
            <button onClick={handleClick}>입력</button>
        </>
    )
}

module.exports = WorldRelay;