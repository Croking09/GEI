import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';
import * as selectors from '../selectors';
import backend from '../../../backend';
import {Pager} from '../../common';
import Bids from './Bids';

const FindBidsResult = () => {

    const bidSearch = useSelector(selectors.getBidSearch);
    const dispatch = useDispatch();

    const handleBackNext = async (backClicked, criteria) => {

        dispatch(actions.clearBidSearch());

        let newCriteria;

        if (backClicked) {
            newCriteria = {page: criteria.page-1}
        } else {
            newCriteria = {page: criteria.page+1}
        }

        const response = await backend.bidService.findUserBids(newCriteria);

        if (response.ok) {
            dispatch(actions.findBidsCompleted({criteria: newCriteria, result: response.payload}));

        }

    }

    if (!bidSearch) {
        return null;
    }

    if (bidSearch.result.items.length === 0) {
        return (
            <div className="alert alert-info" role="alert">
                <FormattedMessage id='project.bid.FindBidsResult.noBids'/>
            </div>
        );
    }

    return (

        <div>
            <Bids bids={bidSearch.result.items}/>
            <Pager
                back={{
                    enabled: bidSearch.criteria.page >= 1,
                    onClick: () => handleBackNext(true, bidSearch.criteria)}}
                next={{
                    enabled: bidSearch.result.existMoreItems,
                    onClick: () => handleBackNext(false, bidSearch.criteria)}}/>
        </div>

    );

}

export default FindBidsResult;
