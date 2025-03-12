import React from 'react';
import './App.css';
import YouTube, { YouTubeProps } from 'react-youtube';

function App() {
  const onPlayerReady: YouTubeProps['onReady'] = (event) => {
    // access to player in all event handlers via event.target
    event.target.pauseVideo();
  }

  return (
    <div className="App">
      <header className="App-header">
         <YouTube videoId="pvIJyRkS9y0" className='h-128' onReady={onPlayerReady} />
        <p>play button</p>
      </header>
    </div>
  );
}

export default App;
