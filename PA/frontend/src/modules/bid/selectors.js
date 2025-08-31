const getModuleState = state => state.bid;

export const getBid = state =>
    getModuleState(state).bid;

export const getBidSearch = state =>
    getModuleState(state).bidSearch;