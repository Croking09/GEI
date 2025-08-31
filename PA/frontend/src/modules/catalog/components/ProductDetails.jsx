import {useState, useEffect} from 'react';
import {useSelector} from 'react-redux';
import {FormattedMessage, FormattedNumber, FormattedDate, FormattedTime} from 'react-intl';
import {useParams} from 'react-router-dom';

import users from '../../users';
import * as selectors from '../selectors';
import backend from '../../../backend';
import {BackLink} from '../../common';

import {PlaceBid} from '../../bid'

const ProductDetails = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    const [product, setProduct] = useState(null);
    const categories = useSelector(selectors.getCategories);
    const {id} = useParams();
    const productId = Number(id);

    const handleBidSuccess = (tiempoRestante, precioActual) => {
        setProduct(prevProduct => ({
                ...prevProduct,
                tiempoRestante,
                valorActual: precioActual,
                tienePujas: true
            }));
    };

    useEffect(() => {

        const findProductById = async productId => {
            if (!Number.isNaN(productId)) {
                const response = await backend.catalogService.findProductById(productId);
                if (response.ok) {
                    setProduct(response.payload);
                }
            }
        }

        findProductById(productId);

    }, [productId]);

    if (!product) {
        return null;
    }

    return (

        <div>

            <BackLink/>

            <div className="card text-center">
                <div className="card-body">

                    <h3 id="productDetailsName" className="card-title font-weight-bold">{product.nombre}</h3>

                    <h6 id="productDetailsCategory" className="card-subtitle text-muted font-weight-bold">
                        <FormattedMessage id='project.global.fields.category'/>:&nbsp;
                            {selectors.getCategoryName(categories, product.idCategoria)}
                    </h6>

                    <p id="productDetailsDescription" className="card-text">
                        <FormattedMessage id='project.global.fields.description'/>{': '}
                        {product.descripcion}
                    </p>

                    <p id="productDetailsPublisher" className="card-text">
                        <FormattedMessage id='project.global.fields.publisher'/>{': '}
                        {product.publicador}
                    </p>

                    <p id="productDetailsPublishingDate" className="card-text">
                        <FormattedMessage id='project.global.fields.publishingDate'/>{': '}
                        <FormattedDate value={new Date(product.fechaPublicacion)}/> - <FormattedTime value={new Date(product.fechaPublicacion)}/>
                    </p>

                    <p id="productDetailsOutPrice" className="card-text">
                        <FormattedMessage id='project.global.fields.outPrice'/>{': '}
                        <FormattedNumber value={product.precioSalida} style="currency" currency="EUR"/>
                    </p>

                    <p id="productDetailsDeliveryInformation" className="card-text">
                        <FormattedMessage id='project.global.fields.informacionEnvio'/>{': '}
                        {product.informacionEnvio}
                    </p>

                    <p id="productDetailsCurrentPrice" className="card-text font-weight-bold">
                        <FormattedMessage id='project.global.fields.currentPrice'/>{': '}
                        <FormattedNumber value={product.valorActual} style="currency" currency="EUR"/>
                    </p>

                    <p id="productDetailsBidTime" className="card-text font-weight-bold">
                        <FormattedMessage id='project.global.fields.bidTimeLeft'/>{': '}
                        {product.tiempoRestante <= 0 ? (
                            <FormattedMessage id="project.global.fields.bidTimeExpired" />
                        ) : (
                            <>
                                {product.tiempoRestante + " "}
                                <FormattedMessage id="project.global.fields.minutes" />
                            </>
                        )}
                    </p>

                </div>
            </div>

            <br/>

            {loggedIn &&
                <div className="card text-center">
                    <div className="card-body">
                        <h3 id="bidFormTitle" className="card-title font-weight-bold">
                            <FormattedMessage id="project.global.fields.bid"/>
                        </h3>
                        <PlaceBid id="bidForm" productId={productId} onBidSuccess={handleBidSuccess} tienePujas={product.tienePujas} valorActual={product.valorActual}/>
                    </div>
                </div>
            }

        </div>

    );

}

export default ProductDetails;
