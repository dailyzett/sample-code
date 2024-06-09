const React = require('react');
const ReactDOM = require('react-dom');
const RockPaperScissors = require('./RockPaperScissors')
const {createRoot} = require("react-dom/client");

const root = createRoot(document.getElementById('root'));
root.render(<RockPaperScissors/>)