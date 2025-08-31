import {Link} from 'react-router-dom';

const ProductLink = ({id, name}) => {
    
    return (
        <Link to={`/catalog/product-details/${id}`}>
            {name}
        </Link>
    );

}

export default ProductLink; 
