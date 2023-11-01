package com.thirty.insitewriteservice.realtime.service;

import org.springframework.stereotype.Service;

import com.thirty.insitewriteservice.realtime.repository.RealtimeDataRepository;
import com.thirty.insitewriteservice.write.dto.DataReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RealtimeServiceImpl implements RealtimeService {
	private final RealtimeDataRepository realtimeDataRepository;

	@Override
	public void writeRealData(DataReqDto dataReqDto) {
		realtimeDataRepository.save(dataReqDto.toRealtimeDataOf());
	}
}
