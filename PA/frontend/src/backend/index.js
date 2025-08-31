import {init} from './appFetch';
import * as userService from './userService';
import * as catalogService from './catalogService'
import * as bidService from './bidService'
import * as productsService from './productService.js'

export {default as NetworkError} from "./NetworkError";

export default {init, userService, catalogService, bidService, productsService};
