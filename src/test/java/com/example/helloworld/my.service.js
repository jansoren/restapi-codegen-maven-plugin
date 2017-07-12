/* eslint-disable max-len*/
import axios from 'axios';

const hostname = 'https://localhost:1234';

export const Something = (id, name) => ({ id, name });

export const getSomething = () => axios.get(`${hostname}/get`);
export const addSomething = something => axios.post(`${hostname}/add`);
export const putSomething = something => axios.put(`${hostname}/put`);
export const putSomething2 = (string, something) => axios.put(`${hostname}/put/${id}`);
export const putSomething3 = (string, int) => axios.put(`${hostname}/put/${id}`);
export const deleteSomething = () => axios.delete(`${hostname}/delete`);
export const headSomething = () => axios.head(`${hostname}/head`);
