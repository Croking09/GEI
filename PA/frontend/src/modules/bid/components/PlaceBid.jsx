import {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedNumber} from 'react-intl';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import {Success} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';
import backend from '../../../backend';

const PlaceBid = ({productId, onBidSuccess, tienePujas, valorActual}) => {

    const dispatch = useDispatch();
    const [bidValue, setBidValue] = useState(0);
    const [backendErrors, setBackendErrors] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);
    const [bidState, setBidState] = useState("----");
    let form;

    const handleSubmit = async event => {
            event.preventDefault();

            if (form.checkValidity()) {
                const response = await backend.bidService.bid(bidValue, productId)

                if (response.ok) {
                    setSuccessMessage("¡Puja realizada con éxito!");
                    setBackendErrors(null);

                    const { estadoPuja, precioActual, tiempoRestante } = response.payload;

                    if (onBidSuccess) {
                        onBidSuccess(tiempoRestante, precioActual); // actualiza los datos del producto
                        setBidValue(0);
                        setBidState(estadoPuja);
                    }

                } else {
                    setSuccessMessage(null);
                    setBackendErrors(response.payload);
                    setBidState("----");
                }
            } else {
                setSuccessMessage(null);
                setBackendErrors(null);
                setBidState("----");
                form.classList.add('was-validated');
            }
    }

    const handleChange = (e) => {
        const floatBidValue = parseFloat(e.target.value);
        setBidValue(isNaN(floatBidValue) ? "" : floatBidValue);
    };

    return (
        <div>
            <div id="bidError">
                <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            </div>
            <div id="bidSuccess">
                <Success message={successMessage} onClose={() => setSuccessMessage(null)}/>
            </div>
            <div id="bidMinValue" className="mb-3 font-weight-bold">
                <FormattedMessage id={tienePujas
                    ? 'project.bid.fields.minBidValue2'
                    : 'project.bid.fields.minBidValue1'
                } />
                {' '}
                <FormattedNumber value={valorActual} style="currency" currency="EUR" />
            </div>
            <form ref={node => form = node}
                className="needs-validation"
                noValidate
                onSubmit={e => handleSubmit(e)}>
                <div className="form-group row">
                    <label id="bidValueLabel" htmlFor="bidValue" className="offset-md-4 col-form-label">
                        <FormattedMessage id="project.global.fields.bidValue"/>:
                    </label>
                    <div className="col-md-2">
                        <input type="number"
                            id="bidValue"
                            className="form-control"
                            step="any"
                            value={bidValue}
                            onChange={handleChange}
                            min="0"
                            required
                        />
                    </div>
                    <button id="bidButton" type="submit" className="btn btn-primary">
                        <FormattedMessage id='project.global.buttons.bid'/>
                    </button>
                </div>
            </form>
            <div id="bidState" className="card-text font-weight-bold">
                <FormattedMessage id='project.global.fields.bidState'/>{': '}
                <FormattedMessage
                    id={`project.bid.status.${bidState}`}
                />
            </div>
        </div>
    );

}

export default PlaceBid;