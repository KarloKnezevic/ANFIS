online = dlmread('online_epochError.txt');
offline = dlmread('offline_epochError.txt');

plot(online(:,1), log10(online(:,2)), 'r');
hold on;
plot(offline(:,1), log10(offline(:,2)), 'g');
xlabel('epoch');
ylabel('log10(error)');
title('Mean Quadratic Error Per Epoch');

legend('online learning error', 'offline learning error');

print('-djpeg','epochError.jpg');