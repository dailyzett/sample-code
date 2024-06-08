const React = require('react');
const ReactDOM = require('react-dom');
const ResponseCheck = require('./ResponseCheck')
const {createRoot} = require("react-dom/client");

const root = createRoot(document.getElementById('root'));
root.render(<ResponseCheck/>)