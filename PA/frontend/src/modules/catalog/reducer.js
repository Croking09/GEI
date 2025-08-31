import * as actionTypes from './actionTypes'
import {combineReducers} from "redux";

const initialState = {
    categories: null,
    productSearch: null,
};

const categories = (state = initialState.categories, action) => {
    switch (action.type) {
        case actionTypes.FIND_ALL_CATEGORIES_COMPLETED:
            return action.categories;

        default:
            return state;
    }
}

const productSearch = (state = initialState.productSearch, action) => {
    switch (action.type) {

        case actionTypes.FIND_PRODUCTS_COMPLETED:
            return action.productSearch;

        case actionTypes.CLEAR_PRODUCT_SEARCH:
            return initialState.productSearch;

        default:
            return state;

    }
}

const reducer = combineReducers({
    categories,
    productSearch
});

export default reducer;