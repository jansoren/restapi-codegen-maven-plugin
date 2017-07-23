/* eslint-disable max-len*/
import axios from 'axios';

const hostname = 'https://localhost:1234';

export const Something = (id, name, number) => ({ id, name, number });

export const getSomething = () => axios.get(`${hostname}/get`);
export const addSomething = something => axios.post(`${hostname}/add`, something);
export const putSomething = something => axios.put(`${hostname}/put`, something);
export const putSomething2 = (id, something) => axios.put(`${hostname}/put/${id}`, something);
export const putSomething3 = (id, int) => axios.put(`${hostname}/put/${id}`, int);
export const deleteSomething = () => axios.delete(`${hostname}/delete`);
export const headSomething = () => axios.head(`${hostname}/head`);
