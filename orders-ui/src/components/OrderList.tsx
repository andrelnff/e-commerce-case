"use client";

import React from "react";
import OrderDetails from "./OrderDetails";
import AsyncWrapper from "./AsyncWrapper";
import { useOrders } from "../hooks/useOrders";

const OrderList = () => {
  const { orders, loading, error, refetch } = useOrders();
  return (
    <AsyncWrapper
      loading={loading}
      error={error}
      isEmpty={orders.length === 0}
      loadingMessage="Carregando pedidos..."
      emptyMessage="Nenhum pedido encontrado. Os pedidos aparecerão aqui quando forem criados."
    >
      <div className="space-y-4">
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-semibold" style={{ color: 'var(--foreground)' }}>
            Pedidos ({orders.length})
          </h2>
        </div>
        <div className="grid gap-4">
          {orders.map((order, index) => (
            <OrderDetails key={index} order={order} />
          ))}
        </div>
      </div>
    </AsyncWrapper>
  );
};

export default OrderList;