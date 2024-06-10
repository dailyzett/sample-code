import React, {useCallback} from 'react';
import {CHANGE_TURN, CLICK_CELL} from './TicTacTo';

const Td = ({rowIndex, cellIndex, dispatch, cellData}) => {
    const onClickTd = useCallback(() => {
        console.log(CLICK_CELL);
        console.log(rowIndex, cellIndex);
        dispatch({type: CLICK_CELL, row: rowIndex, cell: cellIndex});
        dispatch({type: CHANGE_TURN});
    }, [cellData]);

    return (
        <>
            <td onClick={onClickTd}>{cellData}</td>
        </>
    )
}

export default Td;