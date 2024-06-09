const React = require('react');
const ReactDOM = require('react-dom');
const Lotto = require('./Lotto')
const {createRoot} = require("react-dom/client");

const root = createRoot(document.getElementById('root'));
root.render(<Lotto/>)