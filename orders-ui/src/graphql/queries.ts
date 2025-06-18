import { gql } from "@apollo/client";

export const GET_ORDERS = gql`
  query GetOrders {
    placedOrders {
      id
      order
      origin
      total
      createdAt
      items {
        name
        image
        qty
        cost
        currency
      }
    }
  }
`;
