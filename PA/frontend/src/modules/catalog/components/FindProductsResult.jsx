import {useDispatch, useSelector} from "react-redux";

import * as selectors from '../selectors';
import * as actions from "../actions";
import backend from "../../../backend/index.js";
import {FormattedMessage} from "react-intl";
import Products from "./Products.jsx";
import {Pager} from "../../common/index.js";

const FindProductsResult = () => {
    const productSearch = useSelector(selectors.getProductSearch);
    const categories = useSelector(selectors.getCategories);
    const dispatch = useDispatch();

    const handleBackNext = async (backClicked, criteria) => {
        dispatch(actions.clearProductSearch());

        let newCriteria;

        if (backClicked) {
            newCriteria = {...criteria, page: criteria.page-1}
        } else {
            newCriteria = {...criteria, page: criteria.page+1}
        }

        const response = await backend.catalogService.findProducts(newCriteria);

        if (response.ok) {
            dispatch(actions.findProductsCompleted({criteria: newCriteria, result: response.payload}));
        }
    }

    if (!productSearch) {
        return null;
    }

    if (productSearch.result.items.length === 0) {
        return (
            <div className="alert alert-danger" role="alert">
                <FormattedMessage id='project.catalog.FindProductsResult.noProductsFound'/>
            </div>
        );
    }

    return (
        <div>
            <Products products={productSearch.result.items} categories={categories}/>
            <Pager
                back={{
                    enabled: productSearch.criteria.page >= 1,
                    onClick: () => handleBackNext(true, productSearch.criteria)}}
                next={{
                    enabled: productSearch.result.existMoreItems,
                    onClick: () => handleBackNext(false, productSearch.criteria)}}/>
        </div>
    );
}

export default FindProductsResult;