import React from "react";
import { Order } from "../types/types";
import { FaGlobe, FaDollarSign, FaBox } from "react-icons/fa";

const OrderSummary = ({ order }: { order: Order }) => {
  return (
    <div className="w-full">      
    <div className="flex items-center gap-3 mb-4">
        <div>
          <h2 className="text-xl font-bold" style={{ color: 'var(--foreground)' }}>
            Pedido #{order.order}
          </h2>
          <p className="text-sm" style={{ color: 'var(--foreground)', opacity: 0.6 }}>
            Criado em {new Date(order.createdAt).toLocaleDateString('pt-BR')}
          </p>
        </div>
      </div>
      
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">        
        <div className="flex items-center gap-3 p-3 bg-gray-50 rounded-lg">
          <FaGlobe style={{ color: 'var(--foreground)' }} className="flex-shrink-0" />
          <div>
            <p className="text-xs font-medium uppercase tracking-wider" style={{ color: 'var(--foreground)', opacity: 0.6 }}>
              Origem
            </p>
            <p className="text-sm font-semibold break-words" style={{ color: 'var(--foreground)' }}>
              {order.origin}
            </p>
          </div>
        </div>
          <div className="flex items-center gap-3 p-3 bg-gray-50 rounded-lg">
          <FaDollarSign style={{ color: 'var(--foreground)' }} className="flex-shrink-0" />
          <div>
            <p className="text-xs font-medium uppercase tracking-wider" style={{ color: 'var(--foreground)', opacity: 0.6 }}>
              Total
            </p>
            <p className="text-sm font-semibold" style={{ color: 'var(--foreground)' }}>
              {order.total.toFixed(2)} {order.items[0]?.currency || 'USD'}
            </p>
          </div>
        </div>
          <div className="flex items-center gap-3 p-3 bg-gray-50 rounded-lg">
          <FaBox style={{ color: 'var(--foreground)' }} className="flex-shrink-0" />
          <div>
            <p className="text-xs font-medium uppercase tracking-wider" style={{ color: 'var(--foreground)', opacity: 0.6 }}>
              Itens
            </p>
            <p className="text-sm font-semibold" style={{ color: 'var(--foreground)' }}>
              {order.items.length} {order.items.length === 1 ? 'item' : 'itens'}
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default OrderSummary;