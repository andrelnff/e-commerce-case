import React from "react";
import Image from "next/image";
import { Item } from "../types/types";
import { getValidImageUrl, formatCurrency, classNames } from "../utils";
import { DEFAULT_IMAGE } from "../constants";

const ItemCard = ({ 
  name, 
  image, 
  qty, 
  cost, 
  currency,
  className 
}: Item & { className?: string }) => {
  const validImageUrl = getValidImageUrl(image, DEFAULT_IMAGE);
  const totalCost = cost * qty;
    return (
    <div className={classNames(
      "bg-gray-50 border border-gray-200 rounded-lg p-4 hover:shadow-md transition-all duration-200",
      className
    )}>
      <div className="flex items-center gap-4">
        <div className="relative flex-shrink-0">
          <Image
            src={validImageUrl}
            alt={name}
            width={64}
            height={64}
            className="rounded-lg w-16 h-16 object-cover border border-gray-200"
            priority={false}
            loading="lazy"
          />
        </div>
        
        <div className="flex-1 min-w-0">
          <h4 className="font-semibold text-base truncate mb-2" style={{ color: 'var(--foreground)' }}>
            {name}
          </h4>
          <div className="flex flex-wrap items-center gap-4 text-sm">
            <div className="flex items-center gap-1">
              <span className="px-2 py-1 rounded-full text-xs font-medium text-white" style={{ backgroundColor: 'var(--primary)' }}>
                Quantidade: {qty}
              </span>
            </div>
            
            <div className="flex items-center gap-1">
              <span className="px-2 py-1 rounded-full text-xs font-medium text-white" style={{ backgroundColor: 'var(--primary)' }}>
                {formatCurrency(cost, currency)}
              </span>
            </div>
            
            <div className="ml-auto">
              <span className="text-sm font-semibold" style={{ color: 'var(--foreground)' }}>
                Total: <span style={{ color: 'var(--primary)' }}>
                  {formatCurrency(totalCost, currency)}
                </span>
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ItemCard;