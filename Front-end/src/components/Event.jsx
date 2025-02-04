import React from 'react'

const Event = ({picture, name, price}) => {
  return (
    <div>
        <img src={picture} alt="picture" />
        <p>{name}</p>
        <p>{price}</p>
    </div>
  )
}

export default Event