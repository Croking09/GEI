import * as actionTypes from './actionTypes'
import {combineReducers} from "redux";

const initialState = {
    bid: null,
    bidSearch: null
};

const bid = (state = initialState.bid, action) => {
    switch (action.type) {
        case actionTypes. BID_COMPLETED:
            return {id: state.id, items: [], bidValue: 0};
        default:
            return state;
    }
}

const bidSearch = (state = initialState.bidSearch, action) => {

    switch (action.type) {

        case actionTypes.FIND_BIDS_COMPLETED:
            return action.bidSearch;

        case actionTypes.CLEAR_BID_SEARCH:
            return initialState.bidSearch;

        default:
            return state;

    }

}

const reducer = combineReducers({
    bid,
    bidSearch
});

export default reducer;