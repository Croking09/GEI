import * as actionTypes from './actionTypes'

export const findBidsCompleted = bidSearch => ({
    type: actionTypes.FIND_BIDS_COMPLETED,
    bidSearch
});


export const clearBidSearch = () => ({
    type: actionTypes.CLEAR_BID_SEARCH
});
