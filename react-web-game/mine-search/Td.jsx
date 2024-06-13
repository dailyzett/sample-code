import React, {memo, useCallback, useContext, useMemo} from 'react';
import {CLICK_MINE, CODE, FLAG_CELL, NORMALIZED_CELL, OPEN_CELL, QUESTION_CELL, TableContext} from "./MineSearch";


const getTdStyle = (code) => {
    switch (code) {
        case CODE.NORMAL:
        case CODE.MINE:
            return {background: '#444'}
        case CODE.OPENED:
            return {background: 'white'}
        case CODE.QUESTION:
        case CODE.QUESTION_MINE:
            return {background: 'yellow'}
        case CODE.FLAG:
        case CODE.FLAG_MINE:
            return {background: 'red'}
        default:
            return {background: 'white'}
    }
}

const getTdText = (code) => {
    switch (code) {
        case CODE.NORMAL:
            return '';
        case CODE.MINE:
            return 'X';
        case CODE.CLICKED_MINE:
            return '펑';
        case CODE.FLAG:
        case CODE.FLAG_MINE:
            return '!';
        case CODE.QUESTION:
        case CODE.QUESTION_MINE:
            return '?';
        default:
            return code || '';
    }
}

const Td = memo(({rowIndex, cellIndex}) => {
    const {tableData, dispatch, halted} = useContext(TableContext)

    const onClickTd = useCallback(() => {
        if (halted) return;
        switch (tableData[rowIndex][cellIndex]) {
            case CODE.OPENED:
            case CODE.FLAG:
            case CODE.FLAG_MINE:
            case CODE.QUESTION:
            case CODE.QUESTION_MINE:
                return;
            case CODE.NORMAL:
                dispatch({type: OPEN_CELL, row: rowIndex, cell: cellIndex});
                return;
            case CODE.MINE:
                dispatch({type: CLICK_MINE, row: rowIndex, cell: cellIndex});
                return;
        }

    }, [tableData[rowIndex][cellIndex], halted])

    const onRightClickTd = useCallback((e) => {
        if (halted) return;
        e.preventDefault();
        switch (tableData[rowIndex][cellIndex]) {
            case CODE.NORMAL:
            case CODE.MINE:
                dispatch({type: FLAG_CELL, row: rowIndex, cell: cellIndex});
                return;
            case CODE.FLAG:
            case CODE.FLAG_MINE:
                dispatch({type: QUESTION_CELL, row: rowIndex, cell: cellIndex});
                return;
            case CODE.QUESTION:
            case CODE.QUESTION_MINE:
                dispatch({type: NORMALIZED_CELL, row: rowIndex, cell: cellIndex});
                return;
            default:
                return;
        }
    }, [tableData[rowIndex][cellIndex], halted]);

    return useMemo(() => (
        <td style={getTdStyle(tableData[rowIndex][cellIndex])}
            onClick={onClickTd}
            onContextMenu={onRightClickTd}>
            {getTdText(tableData[rowIndex][cellIndex])}
        </td>
    ), [tableData[rowIndex][cellIndex]])
});

export default Td;