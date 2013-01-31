file = dlmread('epochError_.txt');

plot(file(:,1), log10(file(:,2)), 'r');
xlabel('epoch');
ylabel('log10(error)');
title('Mean Quadratic Error Per Epoch');

print('-djpeg','epochXError.jpg');