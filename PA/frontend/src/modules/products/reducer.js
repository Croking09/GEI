import * as actionTypes from './actionTypes'
import {combineReducers} from "redux";

const initialState = {
    product: null,
    productSearch: null,
    lastInsertionId: null,
};

const productSearch = (state = initialState.productSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_PRODUCTS_COMPLETED:
            return action.productSearch;

        case actionTypes.CLEAR_PRODUCTS_SEARCH:
            return initialState.productSearch;

        default:
            return state;

    }

}

const lastInsertionId = (state = initialState.lastInsertionId, action) => {

    switch (action.type) {
        case actionTypes.INSERTION_COMPLETED:
            return action.productId;

        default:
            return state;

    }
}

const reducer = combineReducers({
    productSearch,
    lastInsertionId
});

export default reducer;