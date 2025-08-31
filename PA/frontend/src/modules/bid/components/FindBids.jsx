import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import * as actions from '../actions';
import backend from '../../../backend';

const FindBids = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();

    useEffect(() => {

        const findBids = async (criteria) => {
            const response = await backend.bidService.findUserBids(criteria);

            if (response.ok) {
                dispatch(actions.findBidsCompleted({ criteria, result: response.payload }));
            }
        };

        dispatch(actions.clearBidSearch());
        navigate('/bid/find-bids-result');
        findBids({ page: 0 });
    });

    return null;
};

export default FindBids;
