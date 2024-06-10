const React = require('react');
const Td = require('./Td');

const Tr = ({rowData}) => {
    return (
        <>
            <tr>
                {Array(rowData.length).fill().map((td) => (<Td>{''}</Td>))}
            </tr>
        </>
    )
}

module.exports = Tr;