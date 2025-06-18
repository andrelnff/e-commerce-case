import React, { useState } from "react";
import { Order } from "../types/types";
import OrderSummary from "./OrderSummary";
import ItemCard from "./ItemCard";
import Button from "./Button";

const OrderDetails = ({ order }: { order: Order }) => {
  const [isExpanded, setIsExpanded] = useState(false);

  const toggleExpand = () => {
    setIsExpanded(!isExpanded);
  };
  
  return (
    <div className="card-primary hover:shadow-lg transition-all duration-300">
      <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start gap-4">
        <div className="flex-1">
          <OrderSummary order={order} />
        </div>        
        <Button
          onClick={toggleExpand}
          className="btn-primary w-full sm:w-auto sm:min-w-[140px] whitespace-nowrap"
        >
          {isExpanded ? "Ocultar itens" : "Mostrar itens"}
        </Button>
      </div>
      
      {isExpanded && (
        <div className="mt-6 pt-6" style={{ borderTop: '1px solid #e5e7eb' }}>          
        <h3 className="text-lg font-semibold mb-4" style={{ color: 'var(--foreground)' }}>
            Itens do Pedido ({order.items.length})
        </h3>
        <div className="space-y-3">
          {order.items.map((item, index) => (
            <ItemCard key={index} {...item} />
          ))}
        </div>
      </div>
      )}
    </div>
  );
};

export default OrderDetails;