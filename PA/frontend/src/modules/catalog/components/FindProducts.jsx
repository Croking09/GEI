import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {FormattedMessage} from "react-intl";

import CategorySelector from "../../common/components/CategorySelector.jsx";
import * as actions from "../actions";
import backend from "../../../backend";

const FindProducts = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [categoryId, setCategoryId] = useState('');
    const [keywords, setKeywords] = useState('');

    const toNumber = value => value.length > 0 ? Number(value) : null;

    const handleSubmit = async event => {
        event.preventDefault();
        dispatch(actions.clearProductSearch());
        navigate('catalog/find-products-result');

        const criteria = {categoryId: toNumber(categoryId),
            keywords: keywords.trim(), page: 0};
        const response = await backend.catalogService.findProducts(criteria);

        if (response.ok) {
            dispatch(actions.findProductsCompleted({criteria, result: response.payload}));
        }
    }

    return (
        <form className="form-inline mt-2 mt-md-0" onSubmit={e => handleSubmit(e)}>
            <CategorySelector id="categoryId" className="custom-select my-1 mr-sm-2"
                value={categoryId} onChange={e => setCategoryId(e.target.value)}
                displayText='project.catalog.CategorySelector.allCategories' />

            <input id="keywords" type="text" className="form-control mr-sm-2"
                value={keywords} onChange={e => setKeywords(e.target.value)}/>

            <button id="searchProductsButton" type="submit" className="btn btn-primary my-2 my-sm-0">
                <FormattedMessage id='project.global.buttons.search'/>
            </button>
        </form>
    );
}

export default FindProducts;