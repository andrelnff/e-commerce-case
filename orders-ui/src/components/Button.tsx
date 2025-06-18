import React from "react";

interface ButtonProps {
  onClick: () => void;
  children: React.ReactNode;
  className?: string;
}

const Button: React.FC<ButtonProps> = ({ onClick, children, className }) => {
  return (
    <button
      onClick={onClick}
      className={`cursor-pointer transition-colors duration-300 ${className}`}
    >
      {children}
    </button>
  );
};

export default Button;