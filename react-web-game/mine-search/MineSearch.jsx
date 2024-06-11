import React, {createContext, useMemo, useReducer} from 'react';
import Table from "./Table";
import Form from "./Form";

export const CODE = {
    MINE: -7,
    NORMAL: -1,
    QUESTION: -2,
    FLAG: -3,
    QUESTION_MINE: -5,
    CLICKED_MINE: -6,
    OPENED: 0,
}

export const TableContext = createContext({
    tableData: [],
    dispatch: () => {
    },
});

const initialState = {
    tableData: [],
    timer: 0,
    result: 0,
}

export const START_GAME = 'START_GAME'

const reducer = (state, action) => {
    switch (action.type) {
        case START_GAME:
            return {
                ...state,
                tableData: plantMine(action.row, action.cell, action.mine)
            };
        default:
            return state;
    }
};

function MineSearch() {
    const [state, dispatch] = useReducer(reducer, initialState);
    const {timer, result, tableData} = state;
    const value = useMemo(() => (
        {tableData: tableData, dispatch}
    ), [tableData]);

    return (
        <TableContext.Provider value={value}>
            <Form/>
            <div>{timer}</div>
            <Table/>
            <div>{result}</div>
        </TableContext.Provider>
    )
}

export default MineSearch;